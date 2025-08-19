import React from 'react'
import classes from 'components/ui/DownloadApp/imagePhonesWithPettrek.module.scss'
import phonesImage from 'assets/images/phonesWithPettrek.png'

export default function DownloadApp() {
  return (
        <div className={classes.imagePhonesWithPettrek}>
            <img className={classes.imagePhonesWithPettrek__imagePhones}  src={phonesImage} alt="Экраны приложения"/>
            <div className={classes.imagePhonesWithPettrek__pDownload}>
                <a aria-label="Скачать Pettrek для iOS и Android" className={classes.imagePhonesWithPettrek__pDownloadLink} href="#"><span>Скачать </span> приложение</a>
            </div>
        </div>
  )
}
