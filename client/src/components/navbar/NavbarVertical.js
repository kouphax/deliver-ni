import React, { useContext, useEffect, useRef } from 'react';
import { Button, Collapse, CustomInput, Nav, Navbar } from 'reactstrap';
import Scrollbar from 'react-scrollbars-custom';
import Logo from './Logo';
import NavbarVerticalMenu from './NavbarVerticalMenu';
import ToggleButton from './ToggleButton';
import AppContext from '../../context/Context';
import Flex from '../common/Flex';
import routes from '../../routes';
import { navbarBreakPoint } from '../../config';
import useFetch from "../../hooks/useFetch";
import SearchBox from "./SearchBox";

export const categoriesRoutes = {
  name: 'Categories',
  to: '/categories',
  exact: true,
  icon: 'clipboard-list',
  uri: '/api/categories'
};

export const districtsRoutes = {
  name: 'Districts',
  to: '/districts',
  exact: true,
  icon: 'map-marked-alt',
  uri: '/api/districts'
};

export const homeRoute = {
  name: 'Home',
  to: '/',
  exact: true,
  icon: 'home'
};


const NavbarVertical = () => {
  const navBarRef = useRef(null);

  const {
    showBurgerMenu,
    isNavbarVerticalCollapsed,
    setIsNavbarVerticalCollapsed
  } = useContext(AppContext);

  const HTMLClassList = document.getElementsByTagName('html')[0].classList;
  //Control Component did mount and unmount of hover effect
  if (isNavbarVerticalCollapsed) {
    HTMLClassList.add('navbar-vertical-collapsed');
  }

  useEffect(() => {
    return () => {
      HTMLClassList.remove('navbar-vertical-collapsed-hover');
    };
  }, [isNavbarVerticalCollapsed, HTMLClassList]);

  const { data: categories } = useFetch(categoriesRoutes)
  const categoriesMenu = { ...categoriesRoutes, children: categories.map(({ id, name }) => ({ to: `/categories/${id}`, name, facet: 'categories', id}))}

  const { data: districts } = useFetch(districtsRoutes)
  const districtsMenu = { ...districtsRoutes, children: districts.map((name) => ({ to: `/districts/${name}`, name, facet: 'districts', id: name}))}

  //Control mouseEnter event
  let time = null;
  const handleMouseEnter = () => {
    if (isNavbarVerticalCollapsed) {
      time = setTimeout(() => {
        HTMLClassList.add('navbar-vertical-collapsed-hover');
      }, 100);
    }
  };

  return (
    <Navbar expand={navbarBreakPoint} className="navbar-vertical navbar-glass" light>
      <Flex align="center">
        <ToggleButton
          isNavbarVerticalCollapsed={isNavbarVerticalCollapsed}
          setIsNavbarVerticalCollapsed={setIsNavbarVerticalCollapsed}
        />
        <Logo at="navbar-vertical" width={40} />
      </Flex>

      <Collapse
        navbar
        isOpen={showBurgerMenu}
        className="bg-200"
        innerRef={navBarRef}
        onMouseEnter={handleMouseEnter}
        onMouseLeave={() => {
          clearTimeout(time);
          HTMLClassList.remove('navbar-vertical-collapsed-hover');
        }}
      >
        <Scrollbar
          style={{ height: 'calc(100vh - 77px)', display: 'block' }}
          trackYProps={{
            renderer(props) {
              const { elementRef, ...restProps } = props;
              return <span {...restProps} ref={elementRef} className="TrackY" />;
            }
          }}
        >
          <Nav navbar vertical>
            <NavbarVerticalMenu routes={[homeRoute, categoriesMenu, districtsMenu]} />
          </Nav>
        </Scrollbar>
      </Collapse>
    </Navbar>
  );
};

export default NavbarVertical;
