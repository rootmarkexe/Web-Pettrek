import React from 'react'
import classes from 'components/ui/SectionButton/SectionButton.module.scss'
import ButtonAction from '../ButtonAction'

export default function SectionButton({title, blackText, halfWidth}) {
  return (
    <div className={halfWidth? classes.SectionButtonHalf : classes.SectionButton}>
        <div className={classes.SectionButton__link} href="/">
          <p className={blackText ? classes.SectionButton__blackTitle : classes.SectionButton__title}>{title}</p>
          <ButtonAction/>
        </div>
    </div>  
  )
}
