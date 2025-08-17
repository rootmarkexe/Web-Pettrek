import React, { Children, useContext } from 'react'
import classes from 'components/ui/RegisterButton/RegisterButton.module.scss'



export default function RegisterButton({type, children, email, password}) {
  return (
    <button type={type} className={classes.registerButton}>{children}</button>
  )
}
