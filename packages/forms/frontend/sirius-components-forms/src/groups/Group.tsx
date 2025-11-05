/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
import { getCSSColor, IconOverlay } from '@eclipse-sirius/sirius-components-core';
import { useTheme } from '@mui/material/styles';
import ToggleButton from '@mui/material/ToggleButton';
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';
import Typography from '@mui/material/Typography';
import { useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { PropertySection } from '../propertysections/PropertySection';
import { ToolbarAction } from '../toolbaraction/ToolbarAction';
import { GroupProps, GroupStyleProps } from './Group.types';

const useGroupStyles = makeStyles<GroupStyleProps>()((theme, { borderStyle }) => ({
  group: {
    display: 'flex',
    flexDirection: 'column',
    margin: borderStyle ? theme.spacing(0.5) : 0,
    padding: borderStyle ? theme.spacing(0.5) : 0,
    borderWidth: borderStyle?.size || 0,
    borderColor: borderStyle?.color ? getCSSColor(borderStyle.color, theme) : 'transparent',
    borderStyle: borderStyle?.lineStyle || 'solid',
    borderRadius: borderStyle?.radius || 0,
  },
  groupLabelAndToolbar: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  toolbar: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'flex-end',
  },
  toolbarAction: {
    paddingRight: theme.spacing(1),
    whiteSpace: 'nowrap',
  },
  title: {
    whiteSpace: 'nowrap',
    overflow: 'hidden',
    textOverflow: 'ellipsis',
  },
  verticalSections: {
    display: 'flex',
    flexDirection: 'column',
    '& > *': {
      marginBottom: theme.spacing(1),
    },
  },
  adaptableSections: {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))',
    gap: theme.spacing(2),
    '& > *': {
      marginBottom: theme.spacing(2),
    },
  },
  button: {
    paddingTop: '1px',
    paddingBottom: '0px',
  },
}));

export const Group = ({ editingContextId, formId, group, readOnly }: GroupProps) => {
  const props: GroupStyleProps = {
    borderStyle: group.borderStyle,
  };

  const { classes } = useGroupStyles(props);
  const theme = useTheme();
  const [visibleWidgetIds, setVisibleWidgetIds] = useState<string[]>([]);

  useEffect(() => {
    setVisibleWidgetIds(group.widgets.map((widget) => widget.id));
  }, [group]);

  let widgetSelector: JSX.Element | null = null;
  if (group.displayMode === 'TOGGLEABLE_AREAS') {
    widgetSelector = (
      <ToggleButtonGroup value={visibleWidgetIds} onChange={(_, newVisibleIds) => setVisibleWidgetIds(newVisibleIds)}>
        {group.widgets.map((widget) => {
          return (
            <ToggleButton className={classes.button} value={widget.id} key={widget.id}>
              <IconOverlay
                iconURLs={widget.iconURL}
                alt={widget.label}
                customIconStyle={{ marginRight: theme.spacing(1) }}
              />
              {widget.label}
            </ToggleButton>
          );
        })}
      </ToggleButtonGroup>
    );
  }

  let toolbar: JSX.Element | null = null;
  if (group.toolbarActions?.length > 0) {
    toolbar = (
      <div className={classes.toolbar}>
        {group.toolbarActions.map((toolbarAction) => (
          <div className={classes.toolbarAction} key={toolbarAction.id}>
            <ToolbarAction
              editingContextId={editingContextId}
              formId={formId}
              readOnly={readOnly}
              widget={toolbarAction}
            />
          </div>
        ))}
      </div>
    );
  }
  return (
    <div className={classes.group} data-testid={`group-${group.label}`}>
      <div className={classes.groupLabelAndToolbar}>
        {group.displayMode === 'TOGGLEABLE_AREAS' ? (
          widgetSelector
        ) : (
          <Typography variant="subtitle1" className={classes.title} gutterBottom>
            {group.label}
          </Typography>
        )}
        {toolbar}
      </div>
      <div className={group.displayMode === 'LIST' ? classes.verticalSections : classes.adaptableSections}>
        {group.widgets
          .filter((widget) => visibleWidgetIds.includes(widget.id))
          .map((widget, index) => (
            <PropertySection
              editingContextId={editingContextId}
              formId={formId}
              widget={widget}
              readOnly={readOnly}
              key={`${widget.id}#${index}`}
            />
          ))}
      </div>
    </div>
  );
};
