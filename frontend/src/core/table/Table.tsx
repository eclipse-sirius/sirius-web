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
import React from 'react';
import PropTypes from 'prop-types';

import { TableItem } from './TableItem';

const propTypes = {
  children: PropTypes.node,
  'data-testid': PropTypes.string.isRequired,
};

export const Table = ({ 'data-testid': dataTestid, children }) => {
  const content = React.Children.map(children, (child, i) => <TableItem key={i}>{child}</TableItem>);
  return <div data-testid={dataTestid}>{content}</div>;
};
Table.propTypes = propTypes;
