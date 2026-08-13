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
import Box from '@mui/material/Box';
import ButtonBase from '@mui/material/ButtonBase';
import Typography from '@mui/material/Typography';
import React, { isValidElement, useContext, useState } from 'react';
import { PanelCollapseContext } from './PanelCollapseContext';
import { PanelCollapseContextValue } from './PanelCollapseContext.types';
import { ViewAccordionContentProps, ViewAccordionProps, ViewAccordionToolbarProps } from './ViewAccordion.types';

export const ViewAccordionToolbar = ({ children }: ViewAccordionToolbarProps) => <>{children}</>;
export const ViewAccordionContent = ({ children }: ViewAccordionContentProps) => <>{children}</>;

export const ViewAccordion = ({ id, title, children }: ViewAccordionProps) => {
  const [expanded, setExpanded] = useState<boolean>(true);

  const { onCollapseChange, viewHeaderHeight } = useContext<PanelCollapseContextValue>(PanelCollapseContext);

  const handleToggle = () => {
    setExpanded(!expanded);
    onCollapseChange(id, expanded);
  };

  const childrenArray = React.Children.toArray(children);
  const toolbar = expanded
    ? childrenArray.find((child) => isValidElement(child) && child.type === ViewAccordionToolbar)
    : null;
  const content = childrenArray.find((child) => isValidElement(child) && child.type === ViewAccordionContent);

  const headerId = `${id}-header`;
  const contentId = `${id}-content`;

  return (
    <Box
      data-testid={`view-${title}`}
      sx={(theme) => ({
        display: 'grid',
        gridTemplateColumns: 'minmax(0, 1fr)',
        gridTemplateRows: `${viewHeaderHeight} ${expanded ? 'minmax(0, 1fr)' : '0px'}`,
        height: '100%',
        overflow: 'hidden',
        backgroundColor: theme.palette.background.paper,
      })}>
      <Box
        sx={(theme) => ({
          display: 'flex',
          flexDirection: 'row',
          alignItems: 'center',
          justifyContent: 'space-between',
          overflow: 'hidden',
          padding: theme.spacing(0.75),
          borderTopWidth: '1px',
          borderTopStyle: 'solid',
          borderTopColor: theme.palette.divider,
          borderBottomWidth: '1px',
          borderBottomStyle: 'solid',
          borderBottomColor: theme.palette.divider,
          backgroundColor: theme.palette.view.background,
          '& .MuiIconButton-root': {
            padding: theme.spacing(0.25),
          },
        })}>
        <ButtonBase
          disableRipple
          id={headerId}
          aria-expanded={expanded}
          aria-controls={contentId}
          onClick={handleToggle}
          data-testid={`view-${title}-toggle`}
          sx={(theme) => ({
            display: 'flex',
            flexDirection: 'row',
            alignItems: 'center',
            justifyContent: 'flex-start',
            flexGrow: 1,
            minWidth: 0,
            height: '100%',
            paddingRight: theme.spacing(1),
            '&.Mui-focusVisible': {
              outline: `1px solid ${theme.palette.primary.main}`,
              outlineOffset: '-1px',
            },
          })}>
          {expanded ? (
            <ExpandMoreIcon sx={{ fontSize: '0.875rem' }} />
          ) : (
            <ChevronRightIcon sx={{ fontSize: '0.875rem' }} />
          )}
          <Typography
            noWrap
            sx={(theme) => ({
              marginRight: theme.spacing(1),
              fontWeight: theme.typography.fontWeightBold,
              color: theme.palette.navigationBar.background,
            })}>
            {title}
          </Typography>
        </ButtonBase>
        {toolbar ? (
          <Box
            sx={(theme) => ({
              display: 'flex',
              alignItems: 'center',
              overflow: 'hidden',
              gap: theme.spacing(0.5),
            })}>
            {toolbar}
          </Box>
        ) : null}
      </Box>
      <Box
        role="region"
        id={contentId}
        aria-labelledby={headerId}
        sx={{
          display: 'grid',
          gridTemplateColumns: 'minmax(0, 1fr)',
          gridTemplateRows: 'minmax(0, 1fr)',
          overflow: 'hidden',
          visibility: expanded ? 'visible' : 'hidden',
        }}>
        {content}
      </Box>
    </Box>
  );
};
