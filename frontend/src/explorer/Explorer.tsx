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
  editingContextId: PropTypes.string.isRequired,
  tree: PropTypes.object.isRequired,
  onExpand: PropTypes.func.isRequired,
  selection: PropTypes.object,
  setSelection: PropTypes.func.isRequired,
};

export const Explorer = ({ editingContextId, tree, onExpand, selection, setSelection }) => {
  return (
    <div className={styles.explorer} data-testid="explorer">
      <Tree
        editingContextId={editingContextId}
        tree={tree}
        onExpand={onExpand}
        selection={selection}
        setSelection={setSelection}
      />
    </div>
  );
};
Explorer.propTypes = propTypes;
