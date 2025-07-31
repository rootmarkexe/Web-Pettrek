import React from 'react'
import styles from './Header.module.scss'
import IconLink from 'components/ui/IconLink'
import consultationIcon from 'assets/icons/consultation.svg'
import shopIcon from 'assets/icons/shop.svg'
import mapIcon from 'assets/icons/map.svg'
import petIcon from 'assets/icons/pet.svg'
import Logo from 'components/ui/Logo'



const HEADER_DATA = [
  {id: 1, className: styles.header__navItem, href: '#', src: consultationIcon, alt: 'Консультация', ariaLabel: 'Записаться на консультацию', width: 24, height: 24},
  {id: 2, className: styles.header__navItem, href: '#', src: shopIcon, alt: 'Магазин', ariaLabel: 'Посетить магазин', width: 24, height: 24},
  {id: 3, className: styles.header__navItem, href: '#', src: mapIcon, alt: 'Карта', ariaLabel: 'Посмотреть магазин', width: 24, height: 24},
  {id: 4, className: styles.header__navItem, href: '#', src: petIcon, alt: 'Питомец', ariaLabel: 'Проведать питомца', width: 24, height: 24}
]

export default function Header() {
  return (
    <header className={styles.header}>
      <div className={styles.header__inner}>
        <Logo
          width={86}
          height={36}
          isLink = {true}
        />
        <nav className={styles.header__nav}>
          <ul className={styles.header__navList}>
            {HEADER_DATA.map(item => {
              return(
                <li key={item.id} className={item.className}>
                  <IconLink
                    href={item.href}
                    src={item.src}
                    alt={item.alt}
                    ariaLabel={item.ariaLabel}
                    width={item.width}
                    height={item.height}
                  />
                </li>
              )
            })}
          </ul>
        </nav>    
      </div>
    </header>
  )
}
