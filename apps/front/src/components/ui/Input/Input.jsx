import React from 'react'
import classes from 'components/ui/Input/Input.module.scss'

export default function Input({type, id, placeholder, value, onChange}) {
    const handleChange = (e) =>{
        onChange(id, e.target.value)
    }
    return (
        <input
            className={classes.input}
            type={type}
            id={id}
            placeholder={placeholder}
            value={value}
            onChange={handleChange}       
        />
    )
}
