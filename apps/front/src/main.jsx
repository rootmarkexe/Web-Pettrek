import { createContext, StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import 'assets/styles/styles.scss'
import App from './App.jsx'

import { store } from 'pages/RegisterPage/store/store.js';

export const AuthContext = createContext({store})

createRoot(document.getElementById('root')).render(
  <AuthContext.Provider value={{ store }}>
    <StrictMode>
      <App />
    </StrictMode>
  </AuthContext.Provider>
);

