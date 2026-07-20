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
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import React, { isValidElement, useContext, useState } from 'react';
import { PanelCollapseContext } from './PanelCollapseContext';
import { PanelCollapseContextValue } from './PanelCollapseContext.types';
import { ViewAccordionContentProps, ViewAccordionProps } from './ViewAccordion.types';

export const ViewAccordionContent = ({ children }: ViewAccordionContentProps) => <>{children}</>;

export const ViewAccordion = ({ id, title, children }: ViewAccordionProps) => {
  const [expanded, setExpanded] = useState<boolean>(true);

  const { onCollapseChange, viewHeaderHeight } = useContext<PanelCollapseContextValue>(PanelCollapseContext);

  const handleChange = (_event: React.SyntheticEvent, isExpanded: boolean) => {
    setExpanded(isExpanded);
    onCollapseChange(id, !isExpanded);
  };

  const childrenArray = React.Children.toArray(children);
  const content = childrenArray.find((child) => isValidElement(child) && child.type === ViewAccordionContent);

  return (
    <Accordion
      expanded={expanded}
      onChange={handleChange}
      disableGutters
      square
      elevation={0}
      data-testid={`view-${title}`}
      slotProps={{
        transition: { timeout: 0 },
      }}
      sx={{
        width: '100%',
        '& .MuiCollapse-root': {
          overflow: 'auto',
        },
        '& .MuiAccordionSummary-content': {
          margin: 0,
        },
      }}>
      <AccordionSummary
        aria-controls={`${title}-content`}
        id={`${title}-header`}
        component="div"
        sx={(theme) => ({
          borderBottomWidth: '1px',
          borderBottomStyle: 'solid',
          borderBottomColor: theme.palette.divider,
          minHeight: viewHeaderHeight,
          maxHeight: viewHeaderHeight,
          '&.Mui-expanded': {
            minHeight: viewHeaderHeight,
            maxHeight: viewHeaderHeight,
          },
          '& .MuiIconButton-root': {
            padding: theme.spacing(0.25),
          },
        })}>
        <Box
          sx={{
            display: 'flex',
            flexDirection: 'row',
            alignItems: 'center',
            justifyContent: 'space-between',
            width: '100%',
          }}>
          <Box
            sx={{
              display: 'flex',
              alignItems: 'center',
            }}>
            {expanded ? <ExpandMoreIcon /> : <ChevronRightIcon />}
            <Typography
              sx={(theme) => ({
                marginTop: theme.spacing(1),
                marginRight: theme.spacing(1),
                marginBottom: theme.spacing(1),
              })}>
              {title}
            </Typography>
          </Box>
        </Box>
      </AccordionSummary>
      <AccordionDetails sx={{ padding: 0, display: 'flex', flexDirection: 'column' }}>{content}</AccordionDetails>
    </Accordion>
  );
};
