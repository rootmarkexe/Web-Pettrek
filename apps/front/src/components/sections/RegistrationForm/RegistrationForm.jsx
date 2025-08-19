import React, { useContext, useState } from 'react';
import { validateForm } from 'pages/RegisterPage/validation.js'
import BirthdaySelect from 'components/sections/BirthdaySelect/BirthdaySelect ';
import Input from 'components/ui/Input';
import RegisterButton from 'components/ui/RegisterButton';
import classes from 'components/sections/RegistrationForm/RegistrationForm.module.scss'
import { observer } from 'mobx-react-lite';
import { AuthContext } from '../../../main'

const REGISTER_DATA = [
  { id: 'fullName', visualHidden: true, type: 'text', placeholder: 'Введите ФИО', title: 'ФИО' },
  { id: 'email', visualHidden: true, type: 'email', placeholder: 'Введите e-mail', title: 'Email' },
  { id: 'password', visualHidden: true, type: 'password', placeholder: 'Введите пароль', title: 'Пароль'},
  { id: 'repeatPassword', visualHidden: true, type: 'password', placeholder: 'Повторите пароль', title: 'Повторите пароль' },
];
const INITIAL_STATE = {
    fullName: '',
    email: '',
    password: '',
    repeatPassword: '',
    birthday: {
        day: '',
        month: '',
        year: ''
    }
}

  function RegistrationForm() {
  const [form, setForm] = useState(INITIAL_STATE);
  const [error, setError] = useState('');
 

    const handleChange = (field, value) => {
        setForm(prev => ({
        ...prev,
        [field]: value
        }));
    };

    const handleChangeDate = (field, value) => {
        setForm(prev => ({
        ...prev,
        birthday: {
          ...prev.birthday,
          [field]: value
          }
          }));
    };

    const {store} = useContext(AuthContext)
    const handleSubmit = (e) => {
      e.preventDefault();
      const error = validateForm(form);
      if (error) {
          setError(error);
          return;
      }
  
      store.registration(form.email, form.password);
      setError(null);
      setForm(INITIAL_STATE);
    }

  return (
    <form onSubmit={handleSubmit}>
      <fieldset style={{ display: 'flex', flexDirection: 'column', maxWidth: '500px', marginInline: 'auto', rowGap: '15px' }}>
      
      <h1>Регистрация</h1>

      {REGISTER_DATA.filter(item => item.id === 'fullName').map(item => (
        <div key={item.id}>
          <label htmlFor={item.id} className={item.visualHidden ? 'visually-hidden' : ''}>
            {item.title}
          </label>
          <Input
            autoComplete={item.id === 'password' ? 'off' : undefined}
            type={item.type}
            id={item.id}
            placeholder={item.placeholder}
            value={form[item.id]}
            onChange={handleChange}
          />
        </div>
      ))}

      <div>
        <BirthdaySelect
            value={form.birthday}
            onChange={handleChangeDate}
        />
      </div>

      {REGISTER_DATA.filter(item => item.id !== 'fullName').map(item => (
        <div key={item.id}>
          <label htmlFor={item.id} className={item.visualHidden ? 'visually-hidden' : ''}>
            {item.title}
          </label>
          <Input
            autoComplete={item.id === 'password' ? 'off' : undefined}
            type={item.type}
            id={item.id}
            placeholder={item.placeholder}
            value={form[item.id]}
            onChange={handleChange}
          />
        </div>
      ))}
      {error ? <div style={{color: 'red'}}>{error}</div> : null}
      <div style={{display: 'flex', alignItems: 'center', justifyContent: 'center', width: '100%'}}>
        <RegisterButton type='submit'>Зарегистрироваться</RegisterButton>
      </div>
      </fieldset>  
    </form>
  );
}
export default observer(RegistrationForm);