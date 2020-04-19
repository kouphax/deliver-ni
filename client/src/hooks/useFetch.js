import { useEffect, useState } from 'react';

const useFetch = ({ uri }) => {
  const [loading, setLoading] = useState(true);
  const [data, setData] = useState([]);

  useEffect(() => {
      fetch(uri)
          .then(response => response.json())
          .then(json => {
              setData(json);
              setLoading(false);
          }).catch(err => {
              //window.location =
      })
  }, [uri]);

  return { loading, setLoading, data, setData };
};

export default useFetch;
