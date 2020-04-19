import React, {useContext} from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';

import 'react-toastify/dist/ReactToastify.min.css';
import 'react-datetime/css/react-datetime.css';
import 'react-image-lightbox/style.css';
import NavbarVertical from "./components/navbar/NavbarVertical";
import useFetch from "./hooks/useFetch";
import NavbarTop from "./components/navbar/NavbarTop";
import Footer from "./components/footer/Footer";
import {GoogleApiWrapper, Map, Marker} from 'google-maps-react';
import {Card, CardBody} from "reactstrap";
import googleMapStyles from "./helpers/googleMapStyles";
import AppContext from "./context/Context";

//
// const places = [
//     {
//         "name": "World Foods",
//         "type": "Asian Grocery Shop",
//         "site": "https://www.worldfoodsgroup.co.uk/",
//         "address": "5 Church St\nBangor\nBT20 3HX\n",
//         "position": {
//             "lat": 54.6555855,
//             "lng": -5.6786495
//         },
//         "collection": true,
//         "delivery": true,
//         "coverage": [
//             "BT16-BT23"
//         ],
//         "keywords": [
//             "Asian",
//             "Grocery"
//         ]
//     },
//     {
//         "name": "Bill McCann's",
//         "type": "Butcher",
//         "site": "https://www.facebook.com/Bill-Mccanns-932573796763055/",
//         "address": "89 Cregagh Road\nBelfast\nBT6 8PY\n",
//         "position": {
//             "lat": 54.5836717,
//             "lng": -5.9010437
//         },
//         "collection": true,
//         "delivery": true,
//         "coverage": [
//             "BELFAST"
//         ],
//         "keywords": [
//             "Butcher"
//         ]
//     },
//     {
//         "name": "B's Gluten Free Kitchen",
//         "type": "Bakery",
//         "site": "https://www.facebook.com/AlbesGlutenFreeKitchen/",
//         "address": "19 Enterprise Road\nBangor\nBT19 7TA\n",
//         "position": {
//             "lat": 54.6397324,
//             "lng": -5.6721107
//         },
//         "collection": true,
//         "delivery": true,
//         "coverage": [
//             "BELFAST",
//             "BANGOR"
//         ],
//         "keywords": [
//             "Gluten Free",
//             "Bakery"
//         ]
//     }
// ]

const toQuery = ({categories, districts}) =>
    [].concat(
        ...categories.map(c => `category=${c}`),
        ...districts.map(d => `district=${d}`)
    ).join("&")

const MapContainer = ({google}) => {

    const {searchParams} = useContext(AppContext)

    const query = toQuery(searchParams)
    const uri = `/api/places?${query}`
    const {data: places} = useFetch({uri})

    console.log(searchParams)
    console.log(places)
    return (
        <Map
            styles={googleMapStyles['Default']}
            google={google}
            initialCenter={{lat: 54.5890594, lng: -6.9055877}}
            zoom={9}
            streetViewControl={false}
            mapTypeControl={false}
            fullscreenControl={false}
        >
            {places && places.map(place => {
                return (
                    <Marker
                        key={place.name}
                        position={place.position}
                    />
                );
            })}
        </Map>);
};

const GMap = GoogleApiWrapper({
    //apiKey: "AIzaSyC_cdAqfdW_VAMi6O8xWPdh44lM2T9h58Y",
    apiKey: "",
    version: "3.38"
})(MapContainer);


const Dashboard = () => {
    return (
        <Card className="mb-3">
            <CardBody>
                <div className="position-relative min-vh-50 rounded-soft">
                    <GMap/>
                </div>
            </CardBody>
        </Card>
    )
}

const App = () => {
    return (
        <Router basename={process.env.PUBLIC_URL}>
            <div className='container'>
                <NavbarVertical/>
                <div className="content">
                    <NavbarTop/>
                    <Switch>
                        <Route path="/" exact component={Dashboard}/>
                    </Switch>
                    <Footer/>
                </div>
            </div>
        </Router>
    );
};

export default App;
