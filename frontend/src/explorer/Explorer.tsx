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
import PropTypes from 'prop-types';
import React from 'react';
import { Tree } from 'tree/Tree';
import styles from './Explorer.module.css';

const propTypes = {
  tree: PropTypes.object.isRequired,
  onExpand: PropTypes.func.isRequired,
  selections: PropTypes.array.isRequired,
  setSelections: PropTypes.func.isRequired,
};

export const Explorer = ({ tree, onExpand, selections, setSelections }) => {
  return (
    <div className={styles.explorer} data-testid="explorer">
      <Tree tree={tree} onExpand={onExpand} selections={selections} setSelections={setSelections} />
    </div>
  );
};
Explorer.propTypes = propTypes;
