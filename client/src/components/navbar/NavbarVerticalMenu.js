import React, {useContext, useEffect, useState} from 'react';
import PropTypes from 'prop-types';
import {Collapse, Nav, NavItem, NavLink as BootstrapNavLink} from 'reactstrap';
import {withRouter} from 'react-router-dom';
import NavbarVerticalMenuItem from './NavbarVerticalMenuItem';
import AppContext from '../../context/Context';

const NavbarVerticalMenu = ({ routes, location }) => {
  const [openedIndex, setOpenedIndex] = useState([]);
  const { setShowBurgerMenu } = useContext(AppContext);
  useEffect(() => {
    let openedDropdown = [];
    routes.forEach((route, index) => {
      if (location.pathname.indexOf(route.to) === 0) openedDropdown.push(index);
    });

    setOpenedIndex(openedDropdown);
    // eslint-disable-next-line
  }, []);

  const toggleOpened = (e, index) => {
    e.preventDefault();
    if(openedIndex.indexOf(index) === -1) {
      setOpenedIndex([...openedIndex, index])
    } else {
      setOpenedIndex(openedIndex.filter(x => x !== index))
    }
  };

  return routes.map((route, index) => {
    if (!route.children) {
      return (
        <NavItem key={index}>
          <div className="nav-link" >
            <NavbarVerticalMenuItem route={route} />
          </div>
        </NavItem>
      );
    }

    return (
      <NavItem key={index}>
        <BootstrapNavLink
          onClick={e => toggleOpened(e, index)}
          className="dropdown-indicator cursor-pointer"
          aria-expanded={openedIndex.indexOf(index) !== -1}
        >
          <NavbarVerticalMenuItem route={route} />
        </BootstrapNavLink>
        <Collapse isOpen={openedIndex.indexOf(index) !== -1}>
          <Nav>
            <NavbarVerticalMenu routes={route.children} location={location} />
          </Nav>
        </Collapse>
      </NavItem>
    );
  });
};

NavbarVerticalMenu.propTypes = {
  routes: PropTypes.array.isRequired,
  location: PropTypes.object.isRequired
};

export default withRouter(NavbarVerticalMenu);
