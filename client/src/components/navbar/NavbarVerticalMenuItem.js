import React, {useContext, useState} from 'react';
import PropTypes from 'prop-types';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {Badge, CardBody, CustomInput, Label} from 'reactstrap';
import Flex from '../common/Flex';
import AppContext from "../../context/Context";

const NavbarVerticalMenuItem = ({ route }) => {
  const [checked, setChecked]  = useState(false)
  const { searchParams, setSearchParams } = useContext(AppContext)
  return (
      <Flex align="center">
        {route.icon && (
            <span className="nav-link-icon">
        <FontAwesomeIcon icon={route.icon} className="fa-fw" />
      </span>
        )}

        {(!route.icon) && (
            <CustomInput id={route.id} type="checkbox" checked={checked} onChange={() => {
              const isChecked = !checked
              setChecked(isChecked)
              if(route.facet) {

                if(isChecked) {
                  setSearchParams({...searchParams, [route.facet]: [].concat(...searchParams[route.facet], route.id)})
                } else {
                  setSearchParams({...searchParams, [route.facet]: [].concat(...searchParams[route.facet].filter(x => x !== route.id))})
                }
              }
            }}/>
        )}

        <Label for={route.name} style={{marginBottom: 0, fontSize: "1.1rem"}}><span className="nav-link-text">{route.name}</span></Label>
        {!!route.badge && (
            <Badge color={route.badge.color || 'soft-success'} pill className="ml-2">
              {route.badge.text}
            </Badge>
        )}
      </Flex>
  );
}

NavbarVerticalMenuItem.propTypes = {
  route: PropTypes.shape({
    icon: PropTypes.oneOfType([PropTypes.array, PropTypes.string]),
    name: PropTypes.string.isRequired
  }).isRequired
};

export default React.memo(NavbarVerticalMenuItem);
