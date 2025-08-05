import React from 'react'
import classes from 'components/sections/Footer/Footer.module.scss'
import Logo from 'components/ui/Logo'

export default function Footer() {
  return (
    <section className={classes.footer}>
        <div className={`${classes.footer__inner} container`}>
            <div className={classes.footer__logo}>
                <Logo width={132} height={55} isLink = {true}/>
            </div>
            <div className={classes.footer__contacts}>
                <a href="tel:+79953722140">+7 995 372-21-40</a>
                <a href="mailto: pettrek@mail.ru">pettrek@mail.ru</a>
            </div>
            
        </div>
    </section>
  )
}
