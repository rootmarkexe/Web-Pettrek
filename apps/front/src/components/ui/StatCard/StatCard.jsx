import React from 'react'
import classes from 'components/ui/StatCard/StatCard.module.scss'
import ButtonAction from '../ButtonAction'

export default function StatCard({label, value}) {
  return (
    <div className={classes.statItem}>
        <p className={classes.statItem__subtitle}>
            {label} 
            {value && <span className={classes.statItem__statsValue}>{value}</span>}
        </p>
        <div className={classes.statItem__buttonActionContainer}>
              <ButtonAction/>
        </div>
    </div>
  )
}
