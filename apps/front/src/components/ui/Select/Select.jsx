import React from 'react'
import classes from 'components/ui/Select/Select.module.scss'

export default function Select({ value, onChange, options}) {
   const handleChange = (e) => {
    const selectedValue = e.target.value;
    onChange(/^\d+$/.test(selectedValue) 
      ? parseInt(selectedValue, 10) 
      : selectedValue
    );
  };
  return (
    <select className={classes.select} value={+value} onChange={handleChange} style={{width: '33%'}} >
        <option value="" defaultValue></option>
        {options.map(option => {
        const optionValue = option.value ?? option;
        const optionLabel = option.name ?? option;
        return (
            <option key={optionValue} value={optionValue}>
                {optionLabel}
            </option>
        );
      })}
    </select>
  );
}