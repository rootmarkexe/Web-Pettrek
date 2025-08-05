import React from 'react'
import classes from 'components/sections/about/About.module.scss'
import StatCard from 'components/ui/StatCard'
import phonesImage from 'assets/images/phonesWithPettrek.png'

const ABOUT_DATA = [
    {id: 1, value: '100+', lable: 'Клиники'},
    {id: 2, value: '50+', lable: 'Зоомагазины'},
    {id: 3, value: '', lable: 'Карта'},
    {id: 4, value: '', lable: 'Экстренная помощь'}
]

export default function About() {
  return (
    <section className={classes.about}>
        <div className={`${classes.about__inner} container`}>
            <div className={classes.about__navigation}>
                <h1 className={classes.about__title}>Pettrek –<br/> трекер счастья<br/> и здоровья<br/> питомца!</h1>
                <div className={classes.about__statsContainer}>
                    {ABOUT_DATA.map((item) => {
                        return(
                            <StatCard
                                key={item.id} 
                                value={item.value}
                                label={item.lable}
                            />
                        )
                    })}
                </div>
            </div>
            
            <div className={classes.about__imagePhonesWithPettrek}>
                <img className={classes.about__imagePhones}  src={phonesImage} alt="Экраны приложения"/>
                <div className={classes.about__pDownload}>
                    <a aria-label="Скачать Pettrek для iOS и Android" className={classes.about__pDownloadLink} href="#"><span>Скачать </span> приложение</a>
                </div>
            </div>
        </div>
    </section>
  )
}
