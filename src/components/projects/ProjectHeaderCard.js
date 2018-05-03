/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React from 'react';
import PropTypes from 'prop-types';

import { classNames } from '../../common/classnames';

import { Card, HeroTitle } from '../cards/Card';

const PROJECT_HEADER_CARD__CLASS_NAMES = 'projectheadercard';

const propTypes = {
  name: PropTypes.string.isRequired
};

/**
 * The ProjectHeaderCard is used to contain the most important properties of a
 * project and the main actions used to interact with it.
 */
export const ProjectHeaderCard = ({ className, name, ...props }) => {
  const cardClassNames = classNames(PROJECT_HEADER_CARD__CLASS_NAMES, className);
  return (
    <Card className={cardClassNames} {...props}>
      <HeroTitle label={name} />
    </Card>
  );
};
ProjectHeaderCard.propTypes = propTypes;
