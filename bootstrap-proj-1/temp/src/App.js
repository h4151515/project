import 'bootstrap/dist/css/bootstrap.css';
import './App.css';
import {useEffect, useState} from 'react';
import WeatherUI from './component/WeatherUI';
import WeatherAllButton from './component/WeatherAllButton';
import {Container, Row, Col, Card, CardBody} from 'react-bootstrap';
import ClipLoader from "react-spinners/ClipLoader";

function App() {
  const API_KEY = "974b14eae818cd2bc422a807428f2315"; // ref site : https://docs.openweather.co.uk/current#concept

  const [weather, setWeather] = useState(null);
  const [city, setCity] = useState("");
  const cites = ['seoul', 'busan', 'paris', 'tokyo', 'brazilia'];

  let [loading, setLoading] = useState(false);
  let [color, setColor] = useState("#ffffff");

  const getCurrentLocation = () => {
      navigator.geolocation.getCurrentPosition((position) => {
        let lat = position.coords.latitude;
        let lon = position.coords.longitude;
        console.log("getCurrentLocation()", lat, lon);
        getWeather(lat, lon);
      });
  }

  const getWeather = async(lat, lon) => {
      let url = `https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=${API_KEY}&units=metric&lang=kr`
      setLoading(true);
      let resp = await fetch(url) // 비동기 사용을 위해서 await, async 사용
      let data = await resp.json();
      console.log(data.weather[0].description, data.weather[0].main);
      setWeather(data);
      setLoading(false);
  }

  const getWeatherByCity = async() => {
    let url = `https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${API_KEY}&units=metric&lang=kr`
    setLoading(true);
    if (city === "current") {
      return getCurrentLocation()
    }
    let resp = await fetch(url) // 비동기 사용을 위해서 await, async 사용
    let data = await resp.json();
    console.log(data.weather[0].description, data.weather[0].main);
    setWeather(data);
    setLoading(false);
}

  useEffect(() => { // useEffect(함수, 배열) 배열을 안 주면 render를 실행 뒤 바로 실행
    console.log("city : ", city)
    city == "" ? getCurrentLocation() : getWeatherByCity();
  }, [city])

  return (
    <>
    { loading ? <Container className='d-flex justify-content-center mt-5'>
      <ClipLoader
            color={color}
            loading={loading}
            size={150}
            aria-label="Loading Spinner"
            data-testid="loader"
            /> :
      </Container> : 
      <Container>
        <Card >
          <CardBody>
            <Row>
              <Col><WeatherUI weather={weather}/></Col>
            </Row>
          </CardBody>
          <Row>
          <Col className='mt-3 text-center'>
            <WeatherAllButton 
                cites = {cites} 
                // setCity = {setCity} 
                selectdCity = {city} 
                handleCityChange = {handleCityChange}
            />
          </Col>
          </Row>
        </Card>
      </Container>
    }
    </>
  );
}

export default App;
