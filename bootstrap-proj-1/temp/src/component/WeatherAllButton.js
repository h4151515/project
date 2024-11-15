import React, { useState } from 'react'
import Button from 'react-bootstrap/Button';

const WeatherAllButton = ({cites, selectedCity, handleCityChange}) => {
  console.log(cites);
  
  return (
    <>
        <div className='ms-4'>
            <Button variant={`${selectedCity} == "" ? "light" :  "outline-light"}`} className='mx-1' onClick={() => handleCityChange("current")} >현재도시</Button>
            {cites.map((item) => {
              return <Button variant={`${selectedCity} == item ? "primary" :  "outline-primary"}`}  className='mx-1' onClick={() => handleCityChange(item)}>{item}</Button>
            })}
        </div>
    </>
  )
}

export default WeatherAllButton;