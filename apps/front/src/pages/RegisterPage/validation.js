export const validateForm = (form) => {
  if (!form.fullName.trim()) {
    return 'Введите имя';
  }
  

  if (!/^[a-zA-Zа-яА-Я\s]{2,}$/.test(form.fullName.trim())) {
    return 'Имя должно содержать только буквы и быть не короче 2 символов';
  }


  if (!form.birthday.day || !form.birthday.month || !form.birthday.year) {
    return 'Заполните все поля даты рождения';
  }


  const day = parseInt(form.birthday.day, 10);
  const month = parseInt(form.birthday.month, 10);
  const year = parseInt(form.birthday.year, 10);

  const date = new Date(year, month - 1, day);
  const today = new Date();
  today.setHours(0, 0, 0, 0);

  if (date > today) {
    return "Дата не может быть в будущем";
  }


  if (date.getDate() !== day || date.getMonth() !== month - 1) {
    return "Некорректная дата рождения";
  }

  if (!form.email) {
    return 'Введите почту';
  }
  
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(form.email)) {
    return "Некорректный формат email";
  }

  if (!form.password) {
    return 'Введите пароль';
  }
  
  if (form.password.length < 8) {
    return "Пароль должен быть не менее 8 символов";
  }
  
  if (!/[A-Z]/.test(form.password)) {
    return "Пароль должен содержать хотя бы одну заглавную букву";
  }
  
  if (!/[0-9]/.test(form.password)) {
    return "Пароль должен содержать хотя бы одну цифру";
  }

  if (!form.repeatPassword) {
    return 'Введите пароль повторно';
  }
  
  if (form.password !== form.repeatPassword) {
    return 'Пароли не совпадают';
  }

  return null;
};