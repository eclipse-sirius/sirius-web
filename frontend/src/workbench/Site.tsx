/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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

import MuiAccordion from '@material-ui/core/Accordion';
import AccordionDetails from '@material-ui/core/AccordionDetails';
import MuiAccordionSummary from '@material-ui/core/AccordionSummary';
import MuiCollapse, { CollapseProps } from '@material-ui/core/Collapse';
import { makeStyles, withStyles } from '@material-ui/core/styles';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import React, { useState } from 'react';
import { SiteProps } from './Site.types';

const useSiteStyles = makeStyles((theme) => ({
  site: {
    display: 'flex',
    flexDirection: 'column',
  },
  expanded: {
    flexGrow: 1,
    overflow: 'auto',
  },
  accordionDetailsRoot: {
    display: 'block',
    padding: '0px',
  },
}));

const Accordion = withStyles({
  root: {
    display: 'grid',
    gridTemplateColumns: 'minmax(0,1fr)',
    gridTemplateRows: 'min-content minmax(0,1fr)',
    border: '1px solid rgba(0, 0, 0, .125)',
    boxShadow: 'none',
    '&:not(:last-child)': {
      borderBottom: '0px',
    },
    '&:before': {
      display: 'none',
    },
    '&$expanded': {
      margin: '0px',
    },
  },
  expanded: {},
})(MuiAccordion);

const AccordionSummary = withStyles({
  root: {
    borderBottom: '1px solid rgba(0, 0, 0, .125)',
    marginBottom: -1,
    '&$expanded': {
      minHeight: 48,
    },
  },
  content: {
    '&$expanded': {
      margin: '0px',
    },
  },
  expanded: {},
})(MuiAccordionSummary);

const StyledCollapse = withStyles({
  entered: {
    overflow: 'auto',
  },
})(MuiCollapse);

const CustomCollapse = (props: CollapseProps) => {
  const { children, ...collapseProps } = props;

  const handleEntering = (node: HTMLElement, isAppearing: boolean) => {
    node.style.height = 'auto';
  };

  const handleExit = (node: HTMLElement) => {
    node.style.height = 'auto';
  };

  return (
    <StyledCollapse {...collapseProps} onEntering={handleEntering} onExit={handleExit} timeout={0}>
      {children}
    </StyledCollapse>
  );
};

export const Site = ({ editingContextId, selection, setSelection, readOnly, contributions }: SiteProps) => {
  const classes = useSiteStyles();
  const [expanded, setExpanded] = useState<number>(0);

  let classSite = classes.site;

  return (
    <div className={classSite}>
      {contributions.map((contribution, index) => {
        const title = contribution.props.title;
        const Component = contribution.props.component;
        return (
          <Accordion
            key={index}
            square
            expanded={expanded === index}
            className={expanded === index ? classes.expanded : ''}
            onChange={(event, expanded) => {
              if (expanded) {
                setExpanded(index);
              }
            }}
            TransitionComponent={CustomCollapse}
          >
            <AccordionSummary expandIcon={<ExpandMoreIcon />} IconButtonProps={{ size: 'small' }}>
              {title}
            </AccordionSummary>
            <AccordionDetails className={classes.accordionDetailsRoot} data-testid={`${title} AccordionDetails`}>
              <Component
                editingContextId={editingContextId}
                selection={selection}
                setSelection={setSelection}
                readOnly={readOnly}
              />
            </AccordionDetails>
          </Accordion>
        );
      })}
    </div>
  );
};
