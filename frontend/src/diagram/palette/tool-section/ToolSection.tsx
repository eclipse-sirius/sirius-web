/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import { ToolSectionProps } from 'diagram/palette/tool-section/ToolSection.types';
import { Tool } from 'diagram/palette/tool/Tool';
import React, { useCallback, useState } from 'react';
import styles from './ToolSection.module.css';

/**
 * The component used to display a Tool Section.
 *
 * @hmarchadour
 */
export const ToolSection = ({ toolSection, onToolClick }: ToolSectionProps) => {
  const { tools } = toolSection;
  const initialState = {
    expanded: false,
  };
  const [state, setState] = useState(initialState);
  const { expanded } = state;
  const onActiveTool = useCallback(
    (tool) => {
      onToolClick(tool);
      setState((prevState) => {
        return {
          expanded: false,
        };
      });
    },
    [onToolClick]
  );
  const onExpand = useCallback(() => {
    setState((prevState) => {
      return {
        expanded: !prevState.expanded,
      };
    });
  }, []);

  let caretContent;
  if (tools.length > 1) {
    caretContent = (
      <ExpandMoreIcon
        className={styles.arrow}
        style={{ fontSize: 20 }}
        onClick={() => onExpand()}
        data-testid="expand"
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
        <Tool tool={tools[0]} thumbnail={true} onClick={() => onToolClick(tools[0])} />
        {caretContent}
      </div>
      {toolChoiceContent}
    </>
  );
};
