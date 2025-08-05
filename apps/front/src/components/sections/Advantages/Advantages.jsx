import React from 'react'
import classes from 'components/sections/Advantages/Advantages.module.scss'
import circlesBackground from 'assets/icons/circles.svg'
import Logo from 'components/ui/Logo'
import AdvantageCard from 'components/ui/AdvatageCard'

const ADVANTAGES_DATA = [
  {id: 1, className: classes.advantages__quickRecording, title: 'Быстрая запись к ветеринару', subtitle: 'Больше не нужно звонить и ждать в очередях.', tag: 'h2'},
  {id: 2, className: classes.advantages__emergencyAssistance, title: 'Экстренная помощь', subtitle: 'Возможность связаться с врачом в критической ситуации.', tag: 'h2'},
  {id: 3, className: classes.advantages__actuallyBase, title: 'Актуальная база ветеринарных клиник', subtitle: 'с рейтингами, отзывами и удобной картой.', tag: 'h2'},
  {id: 4, className: classes.advantages__onlineOrder, title: 'Онлайн-заказ товаров', subtitle: 'корма, аксессуары, лекарства с доставкой или самовывозом.', tag: 'h2'},
  {id: 5, className: classes.advantages__usefulTips, title: 'Полезные советы', subtitle: 'Статьи по уходу, воспитанию и здоровью питомцев.', tag: 'h2'},
  {id: 6, className: classes.advantages__personalAccount, title: 'Личный кабинет', subtitle: 'Храните историю посещений, следите за вакцинацией и находите избранные товары.', tag: 'h2'}
]

export default function Advantages() {

  return (
    <section className={classes.advantages}>
        <div className={classes.advantages__backgroundContainer} aria-hidden="true">
            <img src={circlesBackground} alt="" className={classes.advantages__backgroundImg} />
            <div className={classes.advantages__backgroundLogo}>
              <Logo
                  width={709}
                  height={294}
                  isLink = {false}
              />
            </div>
        </div>
        {ADVANTAGES_DATA.map((item) => {
          return(
          <div key={item.id} className={item.className}>
            <AdvantageCard
              title={item.title}
              subtitle={item.subtitle}
              tag={item.tag}
            />
          </div>
        )})}
    </section>
  )
}
