import React from 'react';
import Card from 'react-bootstrap/Card';

const WeatherUI = (props) => {

  console.log("props : ", props);
  let desc = props.weather.weather[0].main;
  console.log(props.weather.weather[0].main);

  return (
    <> 
      {/* 삼항 연산자 : {props.weather?.main.temp_max}
          조건부 렌더링 : {props.weather && props.weather.main.temp_max} */}

      <div className='row'>
        <div className='col border p-5 border-5 bg-light bg-opacity-50'>
          <h1 className='display-3'>{props.weather?.main.temp}</h1>
          <p>{props.weather?.name}<span>({props.weather?.weather[0].description})</span></p>
          <p>
            <span>최고 : </span>{props.weather?.main.temp_max} / 
            <span>최저 : </span>{props.weather?.main.temp_max} / 
            <span>체감온도 : </span>{props.weather?.main.feels_like}</p>
        </div>
        
        <div className='col d-flex justify-content-center align-item-center'>
        {
                    desc.includes('Rain') ? <img className="img-thumbnail" style={{ width: 128, height: 128 }} src="../images/rain.png" /> :
                    desc.includes('Clear') ? <img className="img-thumbnail" style={{ width: 128, height: 128 }} src="../images/clear.png" /> :
                    desc.includes('Cloud') ? <img className="img-thumbnail" style={{ width: 128, height: 128 }} src="../images/cloud.png" /> :
                    <img src="../images/rain.png" />
                  }
        </div>
      </div>
    </>
  )
}

export default WeatherUI