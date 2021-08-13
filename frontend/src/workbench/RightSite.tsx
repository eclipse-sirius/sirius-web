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
import MuiCollapse from '@material-ui/core/Collapse';
import { makeStyles, withStyles } from '@material-ui/core/styles';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import { LinksWebSocketContainer } from 'properties/LinksWebSocketContainer';
import { PropertiesWebSocketContainer } from 'properties/PropertiesWebSocketContainer';
import React from 'react';
import { RightSiteProps } from './RightSite.types';

const useSiteStyles = makeStyles((theme) => ({
  site: {
    display: 'grid',
    gridTemplateColumns: 'minmax(0,1fr)',
    gridTemplateRows: 'minmax(0,1fr)',
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

const CustomCollapse = withStyles({
  entered: {
    overflow: 'auto',
  },
})(MuiCollapse);

export const RightSite = ({ editingContextId, selection, readOnly }: RightSiteProps) => {
  const classes = useSiteStyles();

  const [propertiesExpanded, setPropertiesExpanded] = React.useState<boolean>(true);
  const [externalLinksExpanded, setExternalLinksExpanded] = React.useState<boolean>(true);

  return (
    <div className={classes.site}>
      <Accordion
        square
        expanded={propertiesExpanded}
        onChange={(event, isExpanded) => setPropertiesExpanded(isExpanded)}
        TransitionComponent={CustomCollapse as any}>
        <AccordionSummary expandIcon={<ExpandMoreIcon />}>Details</AccordionSummary>
        <AccordionDetails className={classes.accordionDetailsRoot} data-testid={'Details AccordionDetails'}>
          <PropertiesWebSocketContainer editingContextId={editingContextId} selection={selection} readOnly={readOnly} />
        </AccordionDetails>
      </Accordion>
      <Accordion
        square
        expanded={externalLinksExpanded}
        onChange={(event, isExpanded) => setExternalLinksExpanded(isExpanded)}
        TransitionComponent={CustomCollapse as any}>
        <AccordionSummary expandIcon={<ExpandMoreIcon />}>External Links</AccordionSummary>
        <AccordionDetails className={classes.accordionDetailsRoot} data-testid={'External Links AccordionDetails'}>
          <LinksWebSocketContainer editingContextId={editingContextId} selection={selection} />
        </AccordionDetails>
      </Accordion>
    </div>
  );
};
