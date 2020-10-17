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
import React, { useState, useCallback } from 'react';
import { Tool } from 'diagram/palette/tool/Tool';
import { ArrowExpanded } from 'icons/ArrowExpanded';
import styles from './ToolSection.module.css';

/**
 * The component used to display a Tool Section.
 *
 * @hmarchadour
 */
export const ToolSection = ({ toolSection, onToolClick }) => {
  const { defaultTool, tools } = toolSection;
  const initialState = {
    expanded: false,
    activeTool: defaultTool,
  };
  const [state, setState] = useState(initialState);
  const { expanded, activeTool } = state;
  const onActiveTool = useCallback(
    (tool) => {
      onToolClick(tool);
      setState((prevState) => {
        return {
          expanded: false,
          activeTool: tool,
        };
      });
    },
    [onToolClick]
  );
  const onExpand = useCallback(() => {
    setState((prevState) => {
      return {
        expanded: !prevState.expanded,
        activeTool: prevState.activeTool,
      };
    });
  }, []);

  let caretContent;
  if (tools.length > 1) {
    caretContent = (
      <ArrowExpanded
        className={styles.arrow}
        width="20"
        height="20"
        onClick={() => onExpand()}
        data-testid="expand"
        title={toolSection.label}
      />
    );
  }
  let toolChoiceContent;
  if (expanded) {
    toolChoiceContent = (
      <>
        <div className={styles.toolChoiceWrapper}>
          <div className={styles.toolChoice}>
            <div className={styles.toolList}>
              {tools.map((tool) => {
                return <Tool tool={tool} thumbnail={false} onClick={() => onActiveTool(tool)} key={tool.id} />;
              })}
            </div>
          </div>
        </div>
        <div className={styles.modal} onClick={() => onExpand()}></div>
      </>
    );
  }
  return (
    <>
      <div className={styles.toolSection}>
        <Tool tool={activeTool} thumbnail={true} onClick={() => onToolClick(activeTool)} />
        {caretContent}
      </div>
      {toolChoiceContent}
    </>
  );
};
