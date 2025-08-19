import React from 'react'
import About from 'components/sections/About'
import Advantages from 'components/sections/Advantages'
import Footer from 'components/sections/Footer'
import ForYou from 'components/sections/ForYou'
import Header from 'components/sections/Header'

export default function MainPage() {
  return (
    <>
          <Header/>
          <About/>
          <Advantages/>
          <ForYou/>
          <Footer/>
    </>
  )
}
