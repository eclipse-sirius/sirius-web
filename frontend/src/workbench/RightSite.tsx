/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
import { PropertiesWebSocketContainer } from 'properties/PropertiesWebSocketContainer';
import React, { useState } from 'react';
import { RepresentationsWebSocketContainer } from 'representations/RepresentationsWebSocketContainer';
import { RightSiteProps } from './RightSite.types';

const useSiteStyles = makeStyles((theme) => ({
  site: {
    display: 'grid',
    gridTemplateColumns: 'minmax(0,1fr)',
  },
  detailsExpanded: {
    gridTemplateRows: 'minmax(0,1fr) min-content',
  },
  representationsExpanded: {
    gridTemplateRows: 'min-content minmax(0,1fr)',
  },
  accordionDetailsRoot: {
    display: 'block',
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

const DETAILS_PANEL_NAME = 'details';
const REPRESENTATIONS_PANEL_NAME = 'representations';

export const RightSite = ({ editingContextId, selection, setSelection, readOnly }: RightSiteProps) => {
  const classes = useSiteStyles();

  const [expanded, setExpanded] = useState<string>(DETAILS_PANEL_NAME);

  const handleChange = (panel: string) => () => {
    setExpanded(panel);
  };

  let classSite = classes.site;
  if (expanded === DETAILS_PANEL_NAME) {
    classSite = `${classSite} ${classes.detailsExpanded}`;
  } else if (expanded === REPRESENTATIONS_PANEL_NAME) {
    classSite = `${classSite} ${classes.representationsExpanded}`;
  }

  return (
    <div className={classSite}>
      <Accordion
        square
        expanded={expanded === DETAILS_PANEL_NAME}
        onChange={handleChange(DETAILS_PANEL_NAME)}
        TransitionComponent={CustomCollapse}>
        <AccordionSummary expandIcon={<ExpandMoreIcon />} IconButtonProps={{ size: 'small' }}>
          Details
        </AccordionSummary>
        <AccordionDetails className={classes.accordionDetailsRoot} data-testid={'Details AccordionDetails'}>
          <PropertiesWebSocketContainer
            editingContextId={editingContextId}
            selection={selection}
            setSelection={setSelection}
            readOnly={readOnly}
          />
        </AccordionDetails>
      </Accordion>
      <Accordion
        square
        expanded={expanded === 'representations'}
        onChange={handleChange(REPRESENTATIONS_PANEL_NAME)}
        TransitionComponent={CustomCollapse}>
        <AccordionSummary expandIcon={<ExpandMoreIcon />} IconButtonProps={{ size: 'small' }}>
          Representations
        </AccordionSummary>
        <AccordionDetails className={classes.accordionDetailsRoot} data-testid={'Representations AccordionDetails'}>
          <RepresentationsWebSocketContainer
            editingContextId={editingContextId}
            selection={selection}
            setSelection={setSelection}
            readOnly={readOnly}
          />
        </AccordionDetails>
      </Accordion>
    </div>
  );
};
