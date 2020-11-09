/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import { httpOrigin } from 'common/URL';
import { Text } from 'core/text/Text';
import { GenericTool } from 'icons/GenericTool';
import PropTypes from 'prop-types';
import React from 'react';
import styles from './Tool.module.css';

const propTypes = {
  tool: PropTypes.shape({
    id: PropTypes.string.isRequired,
    label: PropTypes.string.isRequired,
    imageURL: PropTypes.string,
  }).isRequired,
  selected: PropTypes.bool,
  onClick: PropTypes.func.isRequired,
  disabled: PropTypes.bool,
  thumbnail: PropTypes.bool,
};

export const Tool = ({ tool, selected, onClick, disabled, thumbnail }) => {
  const { id, label, imageURL } = tool;

  let className = styles.tool;
  if (disabled) {
    className = `${className} ${styles.disabled}`;
  } else if (selected) {
    className = `${className} ${styles.selected}`;
  }

  let image = <GenericTool title={label} style={{ fill: 'var(--daintree)' }} />;
  if (imageURL && imageURL != '/api/images') {
    let imageSrc;
    if (imageURL.startsWith('data:')) {
      imageSrc = imageURL;
    } else {
      imageSrc = httpOrigin + imageURL;
    }
    image = <img height="16" width="16" alt="" src={imageSrc} title={label} />;
  }
  let labelContent;
  if (!thumbnail) {
    labelContent = <Text className={styles.label}>{label}</Text>;
  }

  const onToolClick = () => {
    if (!disabled) {
      onClick(tool);
    }
  };
  const onKeyPress = (event) => {
    const { key } = event;
    // Space or enter will trigger the tool
    if (key === 'Enter' || key === ' ') {
      event.preventDefault();
      onClick(tool);
    }
  };
  return (
    <div
      className={className}
      key={id}
      onClick={onToolClick}
      onKeyPress={onKeyPress}
      tabIndex={0}
      data-testid={`${label} - Tool`}>
      {image}
      {labelContent}
    </div>
  );
};
Tool.propTypes = propTypes;
