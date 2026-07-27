// ============================================================
// utils.js — Utility helper functions
// ============================================================
const Utils = {
  formatCurrency(amount) {
    if (amount == null) return '0 VNĐ';
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(amount);
  },

  formatDate(dateStr) {
    if (!dateStr) return '-';
    const d = new Date(dateStr);
    return d.toLocaleDateString('vi-VN', { day: '2-digit', month: '2-digit', year: 'numeric' });
  },

  formatTime(timeStr) {
    if (!timeStr) return '-';
    return timeStr.substring(0, 5);
  },

  getStatusBadge(status) {
    switch (status) {
      case 'CONFIRMED':
        return '<span class="badge badge-success">✓ Đã xác nhận</span>';
      case 'COMPLETED':
        return '<span class="badge badge-info">★ Đã khám xong</span>';
      case 'CANCELLED':
        return '<span class="badge badge-danger">✗ Đã hủy</span>';
      case 'PENDING':
        return '<span class="badge badge-warning">⏳ Chờ duyệt</span>';
      default:
        return `<span class="badge badge-muted">${status || 'N/A'}</span>`;
    }
  },

  showToast(message, type = 'info', duration = 3000) {
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
      toast.style.animation = 'fadeOut 0.3s ease forwards';
      setTimeout(() => toast.remove(), 300);
    }, duration);
  }
};
