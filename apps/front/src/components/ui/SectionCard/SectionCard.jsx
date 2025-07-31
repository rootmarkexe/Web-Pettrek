import React from 'react'
import classes from 'components/ui/SectionCard/SectionCard.module.scss'
import SectionButton from '../SectionButton'


export default function SectionCard({title, img, blackText, halfWidth}) {
  return (
    <a href='/' className={classes.sectionCard}>
      <div className={classes.sectionCard__imgContainer}>
          <img src={img} alt="Картинка секции"/>
      </div>
      <div className={blackText? classes.sectionCard__buttonAlt : classes.sectionCard__button}>
          <SectionButton title={title} blackText={blackText} halfWidth={halfWidth}/>
      </div>
    </a>
  )
}
