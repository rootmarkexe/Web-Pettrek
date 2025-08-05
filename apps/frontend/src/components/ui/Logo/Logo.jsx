import React from 'react'

export default function Logo({ width = 24, height = 24, isLink}) {
    const imageLogo = <img 
        width={width}
        height={height}
        src='src/assets/icons/logo.svg'
        alt='Pettrek'
    />
  return isLink ? (
     <a href="/">{imageLogo}</a>) : 
     (imageLogo);
};
