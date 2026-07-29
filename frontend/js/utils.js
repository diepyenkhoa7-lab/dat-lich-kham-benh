// ============================================================
// utils.js — Utility helpers & UI toasts
// ============================================================
const Utils = {
  showToast(message, type = 'info') {
    let container = document.querySelector('.toast-container');
    if (!container) {
      container = document.createElement('div');
      container.className = 'toast-container';
      document.body.appendChild(container);
    }
    const toast = document.createElement('div');
    toast.className = `toast toast-${type}`;
    toast.innerText = message;
    container.appendChild(toast);
    setTimeout(() => {
      toast.style.animation = 'fadeOut 0.3s forwards';
      setTimeout(() => toast.remove(), 300);
    }, 3000);
  },

  formatCurrency(amount) {
    if (amount == null) return '0 VNĐ';
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(amount);
  },

  formatDate(dateStr) {
    if (!dateStr) return '';
    const date = new Date(dateStr);
    return date.toLocaleDateString('vi-VN', { year: 'numeric', month: '2-digit', day: '2-digit' });
  },

  formatTime(timeStr) {
    if (!timeStr) return '';
    return timeStr.substring(0, 5);
  },

  getRoleBadge(role) {
    switch (role) {
      case 'ADMIN': return '<span class="badge badge-danger">Admin</span>';
      case 'DOCTOR': return '<span class="badge badge-info">Bác sĩ</span>';
      case 'PATIENT': return '<span class="badge badge-success">Bệnh nhân</span>';
      default: return `<span class="badge badge-muted">${role}</span>`;
    }
  },

  getStatusBadge(status) {
    switch (status) {
      case 'CONFIRMED': return '<span class="badge badge-success">Đã xác nhận</span>';
      case 'COMPLETED': return '<span class="badge badge-info">Đã khám</span>';
      case 'CANCELLED': return '<span class="badge badge-danger">Đã hủy</span>';
      default: return `<span class="badge badge-warning">${status}</span>`;
    }
  }
};
