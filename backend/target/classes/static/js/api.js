// ============================================================
// api.js — HTTP client với relative path & auto Bearer token
// ============================================================
const API_BASE = '/api/v1';

const Api = {
  _getToken() { return localStorage.getItem('accessToken'); },

  async _request(method, path, body = null, auth = true) {
    const headers = { 'Content-Type': 'application/json' };
    if (auth) {
      const token = this._getToken();
      if (token) headers['Authorization'] = `Bearer ${token}`;
    }
    const opts = { method, headers };
    if (body) opts.body = JSON.stringify(body);

    try {
      const res = await fetch(`${API_BASE}${path}`, opts);
      const data = await res.json().catch(() => ({}));

      if (res.status === 401) {
        const refreshed = await this._tryRefresh();
        if (refreshed) return this._request(method, path, body, auth);
        Auth.logout();
        return;
      }
      if (!res.ok) {
        const msg = data.message || data.error || `Lỗi ${res.status}`;
        throw new Error(msg);
      }
      return data;
    } catch (err) {
      if (err.name === 'TypeError') throw new Error('Không thể kết nối đến server.');
      throw err;
    }
  },

  async _tryRefresh() {
    const refreshToken = localStorage.getItem('refreshToken');
    if (!refreshToken) return false;
    try {
      const res = await fetch(`${API_BASE}/auth/refresh`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ refreshToken })
      });
      if (!res.ok) return false;
      const data = await res.json();
      localStorage.setItem('accessToken', data.data?.accessToken || '');
      return true;
    } catch { return false; }
  },

  get: (path, auth = true) => Api._request('GET', path, null, auth),
  post: (path, body, auth = true) => Api._request('POST', path, body, auth),
  put: (path, body, auth = true) => Api._request('PUT', path, body, auth),
  delete: (path, auth = true) => Api._request('DELETE', path, null, auth),

  // ── Auth ──
  auth: {
    register: (body) => Api.post('/auth/register', body, false),
    login: (body) => Api.post('/auth/login', body, false),
    refresh: (body) => Api.post('/auth/refresh', body, false),
  },

  // ── Doctors ──
  doctors: {
    list: (specialtyId) => Api.get(`/doctors${specialtyId ? `?specialtyId=${specialtyId}` : ''}`),
    get: (id) => Api.get(`/doctors/${id}`),
    slots: (id, date) => Api.get(`/doctors/${id}/slots?date=${date}`),
    createSlot: (body) => Api.post('/doctors/slots', body),
    toggleSlot: (id, avail) => Api.put(`/doctors/slots/${id}/toggle?available=${avail}`, null),
    myAppointments: () => Api.get('/doctors/my-appointments'),
    updateMedRecord: (body) => Api.post('/doctors/medical-records', body),
  },

  // ── Appointments ──
  appointments: {
    create: (body) => Api.post('/appointments', body),
    cancel: (id) => Api.delete(`/appointments/${id}/cancel`),
    get: (id) => Api.get(`/appointments/${id}`),
    mine: () => Api.get('/appointments/my-appointments'),
  },

  // ── Admin ──
  admin: {
    pendingDoctors: () => Api.get('/admin/doctors/pending'),
    approveDoctor: (id) => Api.put(`/admin/doctors/${id}/approve`, null),
    rejectDoctor: (id) => Api.put(`/admin/doctors/${id}/reject`, null),
    specialties: () => Api.get('/admin/specialties'),
    createSpecialty: (body) => Api.post('/admin/specialties', body),
    stats: () => Api.get('/admin/stats'),
  },

  // ── Patient ──
  patient: {
    profile: () => Api.get('/patients/me'),
    update: (body) => Api.put('/patients/me', body),
  },
};
