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

import { classNames } from '../../../common/classnames';

import { Button } from '../../buttons/Button';
import { LoadingConsumer } from '../../loading/Loading';
import { Spacing } from '../../spacing/Spacing';
import { M } from '../../spacing/SpacingConstants';
import { Text } from '../../text/Text';

import './ViewDescription.css';

const VIEWDESCRIPTION__CLASS_NAMES = 'viewdescription';
const VIEWDESCRIPTION_TEXT__CLASS_NAMES = 'viewdescription-text';

const viewDescriptionPropTypes = {
  description: PropTypes.string.isRequired,
  onEditClick: PropTypes.func.isRequired
};

/**
 * The ViewDescription component is used to display the description of the
 * project with a button used to edit it.
 */
export const ViewDescription = ({ className, description, onEditClick, ...props }) => {
  const viewDescriptionClassNames = classNames(VIEWDESCRIPTION__CLASS_NAMES, className);
  return (
    <div className={viewDescriptionClassNames} {...props}>
      <LoadingConsumer>
        {loading => (
          <React.Fragment>
            <Text className={VIEWDESCRIPTION_TEXT__CLASS_NAMES} loading={loading}>
              {description}
            </Text>
            <Spacing left={M}>
              <Button onClick={onEditClick} loading={loading}>
                Edit
              </Button>
            </Spacing>
          </React.Fragment>
        )}
      </LoadingConsumer>
    </div>
  );
};
ViewDescription.propTypes = viewDescriptionPropTypes;
