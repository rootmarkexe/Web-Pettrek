import React from 'react'
import styles from 'components/ui/IconLink/IconLink.module.scss'

export default function IconLink({href, src, alt, ariaLabel, width = 24, height = 24}) {
  return (
    <a className={styles.iconLink} href={href} aria-label={ariaLabel}>
        <img 
          className={styles.iconImage} 
          src={src}
          alt={alt}
          width={width}
          height={height} 
        />
    </a>
  )
}
