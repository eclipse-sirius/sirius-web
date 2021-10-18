/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import { Tool } from 'diagram/palette/tool/Tool';
import PropTypes from 'prop-types';
import React, { useCallback } from 'react';
import styles from './ContextualMenu.module.css';
import toolSectionStyles from './tool-section/ToolSection.module.css';

const toolType = PropTypes.shape({
  id: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired,
  imageURL: PropTypes.string,
});
const propTypes = {
  tools: PropTypes.arrayOf(toolType).isRequired,
  invokeTool: PropTypes.func.isRequired,
  invokeClose: PropTypes.func.isRequired,
};

/**
 * The component used to display a Contextual Menu.
 *
 * @hmarchadour
 */
export const ContextualMenu = ({ tools, invokeTool, invokeClose }) => {
  const onToolClick = useCallback(
    (tool) => {
      invokeTool(tool);
    },
    [invokeTool]
  );
  return (
    <>
      <div className={styles.menu} attrs-data-testid={`PopupMenu`}>
        <div className={styles.menuEntries}>
          {tools.map((tool) => (
            <Tool tool={tool} thumbnail={false} onClick={onToolClick} key={tool.id} />
          ))}
        </div>
      </div>

      <div className={toolSectionStyles.modal} onClick={() => invokeClose()}></div>
    </>
  );
};
ContextualMenu.propTypes = propTypes;
