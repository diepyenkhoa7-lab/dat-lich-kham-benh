// ============================================================
// auth.js — Auth session manager với relative path
// ============================================================
const Auth = {
  getUser() {
    const u = localStorage.getItem('user');
    return u ? JSON.parse(u) : null;
  },

  setSession(authData) {
    localStorage.setItem('accessToken', authData.accessToken);
    localStorage.setItem('refreshToken', authData.refreshToken);
    localStorage.setItem('user', JSON.stringify({
      id: authData.userId,
      email: authData.email,
      role: authData.role
    }));
  },

  logout() {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('user');
    window.location.href = '/login.html';
  },

  checkAuth(allowedRoles = []) {
    const user = this.getUser();
    if (!user) {
      window.location.href = '/login.html';
      return null;
    }
    if (allowedRoles.length > 0 && !allowedRoles.includes(user.role)) {
      alert('Bạn không có quyền truy cập trang này!');
      this.redirectByRole(user.role);
      return null;
    }
    return user;
  },

  redirectByRole(role) {
    switch (role) {
      case 'ADMIN': window.location.href = '/admin/dashboard.html'; break;
      case 'DOCTOR': window.location.href = '/doctor/dashboard.html'; break;
      case 'PATIENT': window.location.href = '/patient/dashboard.html'; break;
      default: window.location.href = '/index.html';
    }
  }
};
