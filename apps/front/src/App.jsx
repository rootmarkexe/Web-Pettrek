import React, { useContext, useEffect } from 'react'
import RegisterPage from 'pages/RegisterPage/RegisterPage'
import { AuthContext } from './main'
import { observer } from 'mobx-react-lite'

function App() {
  const {store} = useContext(AuthContext)

  useEffect(() => {
    if(localStorage.getItem('token')){
      store.checkAuth();
    }
  }, [])
  if(store.isLoading){
    return(
      <div>Loading...</div>
    )
  }
  // if(!store.isAuth){
  //   return(
  //     <>
  //       здесь будет логинформ
  //     </>
  //   )
  // }

  return (
    <>
      <RegisterPage/>
    </>
  )
}
export default observer(App);