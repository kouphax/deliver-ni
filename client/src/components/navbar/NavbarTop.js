import React, { useContext } from 'react';
import { Collapse, Navbar, NavItem, Nav, NavLink } from 'reactstrap';
import AppContext, { ProductContext } from '../../context/Context';
import Logo from './Logo';
import SearchBox from './SearchBox';
import ProfileDropdown from './ProfileDropdown';
import NotificationDropdown from './NotificationDropdown';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import classNames from 'classnames';

const NavbarTop = () => {
  const { showBurgerMenu, setShowBurgerMenu } = useContext(AppContext);

  return (
    <Navbar light className="navbar-glass fs--1 font-weight-semi-bold row navbar-top sticky-kit" expand>
      <div className="toggle-icon-wrapper d-xl-none">
        <button
          className="navbar-vertical-toggle btn btn-link d-flex align-item-center justify-content-center "
          onClick={() => {
            setShowBurgerMenu(!showBurgerMenu);
          }}
          id="burgerMenu"
        >
          <span className="navbar-toggle-icon">
            <span className="toggle-line" />
          </span>
        </button>
      </div>
      <Logo at="navbar-top" width={40} id="topLogo" />
      <Collapse navbar>
        <Nav navbar className="align-items-center d-none d-lg-block">
          <NavItem>
            <SearchBox />
          </NavItem>
        </Nav>

        <Nav navbar className="align-items-center ml-auto">
          <NavItem>
            <NavLink
              tag={Link}
              to="/"
            >
                <FontAwesomeIcon icon="bell" transform="shrink-6" className="fs-4" />
            </NavLink>
          </NavItem>
        </Nav>
      </Collapse>
    </Navbar>
  );
};

export default NavbarTop;
