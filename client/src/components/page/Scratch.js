import React from 'react';
import { Alert, Card, CardBody, Col, Row } from 'reactstrap';
import Loader from '../common/Loader';
import FalconCardHeader from '../common/FalconCardHeader';
import Notification from '../notification/Notification';
import { isIterableArray } from '../../helpers/utils';
import useFakeFetch from '../../hooks/useFakeFetch';
import rawActivities from '../../data/activity/activities';
import GoogleMap from '../map/GoogleMap';

const Scratch = () => {
  const { loading, data: activities } = useFakeFetch(rawActivities);

  return (
    <Card>
      <FalconCardHeader title="Places" />
      <GoogleMap
        initialCenter={{
            lat: 48.8583736,
            lng: 2.2922926
        }}
        mapStyle='Default'
        className="min-vh-50 rounded-soft"
        >
        <h5>Eiffel Tower</h5>
        <p>
            Gustave Eiffel's iconic, wrought-iron 1889 tower,
            <br />
            with steps and elevators to observation decks.
        </p>
        </GoogleMap>
    </Card>
  );
};

export default Scratch;
