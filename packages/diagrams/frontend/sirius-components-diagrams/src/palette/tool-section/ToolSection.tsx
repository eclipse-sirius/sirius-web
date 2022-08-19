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
import { makeStyles } from '@material-ui/core/styles';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import { useCallback, useState } from 'react';
import { Tool } from '../tool/Tool';
import { ToolSectionProps } from './ToolSection.types';

const useToolSectionStyles = makeStyles(() => ({
  toolSection: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    marginLeft: '8px',
  },
  toolChoiceWrapper: {
    justifyContent: 'center',
    alignItems: 'center',
  },
  toolChoice: {
    position: 'fixed',
    zIndex: 100,
    marginLeft: '-20px',
    marginTop: '10px',
  },
  toolList: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridGap: '4px',
    padding: '4px',
    background: '#fafafa',
    border: '1px solid #d1dadb',
    boxShadow: '0px 2px 5px #002b3c40',
    borderRadius: '2px',
    color: '#002d37',
  },
  modal: {
    position: 'fixed',
    zIndex: 1,
    left: 0,
    top: 0,
    width: '100%',
    height: '100%',
    overflow: 'auto',
  },
  arrow: {
    cursor: 'pointer',
    height: '14px',
    width: '14px',
    marginLeft: '-4px',
    marginTop: '12px',
  },
}));

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

  const classes = useToolSectionStyles();

  const onActiveTool = useCallback(
    (tool) => {
      onToolClick(tool);
      setState(() => {
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
        className={classes.arrow}
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
        <div className={classes.toolChoiceWrapper}>
          <div className={classes.toolChoice}>
            <div className={classes.toolList}>
              {tools.map((tool) => {
                return <Tool tool={tool} onClick={() => onActiveTool(tool)} key={tool.id} />;
              })}
            </div>
          </div>
        </div>
        <div className={classes.modal} onClick={() => onExpand()}></div>
      </>
    );
  }
  return (
    <>
      <div className={classes.toolSection}>
        <Tool tool={tools[0]} thumbnail onClick={() => onToolClick(tools[0])} />
        {caretContent}
      </div>
      {toolChoiceContent}
    </>
  );
};
