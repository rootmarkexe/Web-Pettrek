import React from 'react'
import classes from 'components/sections/ForYou/ForYou.module.scss'
import SectionCard from 'components/ui/SectionCard'
import mapImg from "assets/images/map.png" 
import clinicsImg from "assets/images/clinics.png" 
import petShopsImg from "assets/images/petShops.png"
import pettrekImg from "assets/images/pettrekSection.png"
import appointmentsImg from "assets/images/appointments.png"
import ambulanceImg from "assets/images/ambulance.png"
import businessImg from "assets/images/business.png"

const FORYOU_DATA = [
  {group: 1, id: 1, title: 'Рядом с вами', img: mapImg, className: classes.ForYou__map,  halfWidth: false},
  {group: 1, id: 2, title: 'Клиники', img: clinicsImg, className: classes.ForYou__clinics,  halfWidth: false},
  {group: 1, id: 3, title: 'Зоомагазины', img: petShopsImg, className: classes.ForYou__petShops, halfWidth: true},
  {group: 1, id: 4, title: 'Личный кабинет', img: pettrekImg, className: classes.ForYou__account, halfWidth: true},
  {group: 2, id: 5, title: 'Экстренная помощь', img: ambulanceImg, className: classes.ForYou__ambulance, blackText: false},
  {group: 2, id: 6, title: 'Ближайшие записи', img: appointmentsImg, className: classes.ForYou__appointments, blackText: false},
  {group: 2, id: 7, title: 'Записывайте и продавайте онлайн!', img: businessImg, className: classes.ForYou__forBusiness, blackText: true}
]



export default function ForYou() {
  return (
    <section className={classes.ForYou}>
        <div className={`${classes.ForYou__inner} container`}>
            <p className={classes.ForYou__description}>Выберите раздел/ Select a section</p>
            <h2 className={classes.ForYou__title}>Для вас <span>/ For you</span></h2>
            <div className={classes.ForYou__firstGrid}>
                {FORYOU_DATA.filter((item) => item.group === 1).map((el) => {
                  return(
                    <div key={el.id} className={el.className}>
                      <SectionCard title={el.title} img={el.img} halfWidth={el.halfWidth}/>
                    </div>
                  )
                })}
            </div>
            <div className={classes.ForYou__secondGrid}>
              {FORYOU_DATA.filter((item) => item.group === 2).map((el) => {
                  return(
                    <div key={el.id} className={el.className}>
                      <SectionCard title={el.title} img={el.img} blackText={el.blackText}/>
                    </div>
                  )
                })}
            </div>
        </div>
    </section>
  )
}
