import Select from 'components/ui/Select/Select';
import React, { useMemo } from 'react';
import classes from 'components/sections/BirthdaySelect/BirthdaySelect .module.scss'

export default function BirthdaySelect({ value, onChange }) {
  
  const [daysOptions, yearsOptions, monthsOptions] = useMemo(() => {
    const currentYear = new Date().getFullYear();
    const days = Array.from({ length: 31 }, (_, i) => ({
      value: i + 1,
      name: (i + 1).toString()
    }));
    
    const years = Array.from({ length: 150 }, (_, i) => ({
      value: currentYear - i,
      name: (currentYear - i).toString()
    }));

    const months = Array.from({ length: 12 }, (_, i) => ({
      value: i + 1,
      name: (i + 1).toString()
    }));
    return [days, years, months];
  }, []);

  const handleDayChange = (val) => onChange('day', val);
  const handleMonthChange = (val) => onChange('month', val);
  const handleYearChange = (val) => onChange('year', val);

  return (
    <div className={classes.birthdaySelect}>
      <h3 className={classes.birthdaySelect__title}>Введите дату рождения</h3>
      <div className={classes.birthdaySelect__inner}>
        <Select 
          value={value.day} 
          onChange={handleDayChange}
          options={daysOptions}
        />
        <Select 
          value={value.month}  
          onChange={handleMonthChange}
          options={monthsOptions}
        />
        <Select 
          value={value.year} 
          onChange={handleYearChange}
          options={yearsOptions}
        />
      </div>    
    </div>
  )
}