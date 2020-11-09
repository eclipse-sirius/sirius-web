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
import { useProject } from 'project/ProjectProvider';
import PropTypes from 'prop-types';
import React from 'react';

const propTypes = {
  requiredAccessLevel: PropTypes.string.isRequired,
  children: PropTypes.node.isRequired,
};

export const Permission = ({ requiredAccessLevel, children }) => {
  const { accessLevel } = useProject() as any;
  return (
    <UnsynchronizedPermission accessLevel={accessLevel} requiredAccessLevel={requiredAccessLevel} children={children} />
  );
};
Permission.propTypes = propTypes;

export const UnsynchronizedPermission = ({ accessLevel, requiredAccessLevel, children }) => {
  let shouldDisable = accessLevel === 'READ' && requiredAccessLevel === 'EDIT';
  shouldDisable = shouldDisable || (accessLevel === 'READ' && requiredAccessLevel === 'ADMIN');
  shouldDisable = shouldDisable || (accessLevel === 'EDIT' && requiredAccessLevel === 'ADMIN');
  return React.cloneElement(children, { disabled: shouldDisable });
};

UnsynchronizedPermission.propTypes = {
  accessLevel: PropTypes.string.isRequired,
  requiredAccessLevel: PropTypes.string.isRequired,
  children: PropTypes.node.isRequired,
};
