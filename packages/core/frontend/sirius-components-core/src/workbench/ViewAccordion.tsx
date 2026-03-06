/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import Accordion from '@mui/material/Accordion';
import AccordionDetails from '@mui/material/AccordionDetails';
import AccordionSummary from '@mui/material/AccordionSummary';
import Typography from '@mui/material/Typography';
import { Theme } from '@mui/material/styles';
import React, { isValidElement, useContext, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { PanelCollapseContext } from './PanelCollapseContext';
import { PanelCollapseContextValue } from './PanelCollapseContext.types';
import { ViewAccordionContentProps, ViewAccordionProps, ViewAccordionToolbarProps } from './ViewAccordion.types';

const useStyles = makeStyles()((theme: Theme) => ({
  accordion: {
    width: '100%',
    display: 'flex',
    flexDirection: 'column',
    height: '100%',
    '& .MuiCollapse-root': {
      overflow: 'auto',
      height: '100%',
    },
    '& .MuiAccordionSummary-content': {
      margin: 0,
    },
  },
  accordionSummary: {
    padding: '6px',
    minHeight: theme.spacing(3.5),
    maxHeight: theme.spacing(3.5),
    borderBottomColor: theme.palette.divider,
    borderBottomWidth: '1px',
    borderBottomStyle: 'solid',
    borderTopColor: theme.palette.divider,
    borderTopWidth: '1px',
    borderTopStyle: 'solid',
    backgroundColor: theme.palette.grey[200],
    '&.Mui-expanded': {
      minHeight: theme.spacing(3.5),
      maxHeight: theme.spacing(3.5),
    },
    '& .MuiIconButton-root': {
      padding: '2px',
    },
  },
  accordionDetails: {
    padding: 0,
    display: 'flex',
    flexDirection: 'column',
  },
  viewHeader: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    width: '100%',
  },
  viewHeaderLeft: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
  },
  viewHeaderTitle: {
    marginRight: theme.spacing(1),
    fontWeight: theme.typography.fontWeightBold,
    color: theme.palette.navigationBar.background,
  },
  viewHeaderRight: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    overflow: 'hidden',
    justifyContent: 'right',
    gap: theme.spacing(0.5),
  },
  expandMoreIcon: {
    fontSize: '0.875rem',
  },
}));

export const ViewAccordionToolbar = ({ children }: ViewAccordionToolbarProps) => <>{children}</>;
export const ViewAccordionContent = ({ children }: ViewAccordionContentProps) => <>{children}</>;

export const ViewAccordion = ({ id, title, children }: ViewAccordionProps) => {
  const { classes } = useStyles();
  const [expanded, setExpanded] = useState<boolean>(true);

  const { onCollapseChange } = useContext<PanelCollapseContextValue>(PanelCollapseContext);

  const handleChange = (_event: React.SyntheticEvent, isExpanded: boolean) => {
    setExpanded(isExpanded);
    onCollapseChange(id, !isExpanded);
  };

  const childrenArray = React.Children.toArray(children);
  const toolbar = childrenArray.find((child) => isValidElement(child) && child.type === ViewAccordionToolbar);
  const content = childrenArray.find((child) => isValidElement(child) && child.type === ViewAccordionContent);

  return (
    <Accordion
      expanded={expanded}
      onChange={handleChange}
      disableGutters
      square
      elevation={0}
      slotProps={{
        transition: { timeout: 0 },
      }}
      className={classes.accordion}>
      <AccordionSummary
        aria-controls={`${title}-content`}
        id={`${title}-header`}
        className={classes.accordionSummary}
        component="div"
        sx={{ height: expanded ? 'auto' : '100%' }}>
        <div className={classes.viewHeader}>
          <div className={classes.viewHeaderLeft}>
            {expanded ? (
              <ExpandMoreIcon className={classes.expandMoreIcon} />
            ) : (
              <ChevronRightIcon className={classes.expandMoreIcon} />
            )}
            <Typography className={classes.viewHeaderTitle}>{title}</Typography>
          </div>
          {toolbar && (
            <div className={classes.viewHeaderRight} onClick={(e) => e.stopPropagation()}>
              {toolbar}
            </div>
          )}
        </div>
      </AccordionSummary>
      <AccordionDetails className={classes.accordionDetails}>{content}</AccordionDetails>
    </Accordion>
  );
};
