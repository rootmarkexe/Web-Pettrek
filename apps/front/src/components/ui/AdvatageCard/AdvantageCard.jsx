import React from 'react'
import classes from 'components/ui/AdvatageCard/AdvantageCard.module.scss'

export default function AdvantageCard({title, subtitle, tag = 'h3'}) {
    const Titletag = tag;
  return (
    <div className={classes.AdvantageCard}>
        <Titletag className={classes.AdvantageCard__title}>{title}</Titletag>
        <p className={classes.AdvantageCard__subtitle}>{subtitle}</p>
    </div>
  )
}
