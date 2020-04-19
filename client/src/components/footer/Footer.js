import React from 'react';
import { Col, Row } from 'reactstrap';
import { version } from '../../config';

const Footer = () => (
  <footer>
    <Row noGutters className="justify-content-between text-right fs--1 mt-4 mb-3">
      <Col>
        <p className="mb-0 text-600">
          <br className="d-sm-none" /> {new Date().getFullYear()} &copy; <a href="https://yobriefca.se">Yo Briefcase Ltd</a>
        </p>
      </Col>
    </Row>
  </footer>
);

export default Footer;
