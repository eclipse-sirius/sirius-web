/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
import { makeStyles, Theme, useTheme } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import ToggleButton from '@material-ui/lab/ToggleButton';
import ToggleButtonGroup from '@material-ui/lab/ToggleButtonGroup';
import { useEffect, useState } from 'react';
import { PropertySection } from '../propertysections/PropertySection';
import { ToolbarAction } from '../toolbaraction/ToolbarAction';
import { GroupProps, GroupStyleProps } from './Group.types';

const useGroupStyles = makeStyles<Theme, GroupStyleProps>((theme) => ({
  group: {
    display: 'flex',
    flexDirection: 'column',
    margin: ({ borderStyle }) => (borderStyle ? theme.spacing(0.5) : 0),
    padding: ({ borderStyle }) => (borderStyle ? theme.spacing(0.5) : 0),
    borderWidth: ({ borderStyle }) => borderStyle?.size || 0,
    borderColor: ({ borderStyle }) => getCSSColor(borderStyle?.color, theme) || 'transparent',
    borderStyle: ({ borderStyle }) => borderStyle?.lineStyle || 'solid',
    borderRadius: ({ borderStyle }) => borderStyle?.radius || 0,
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

  const classes = useGroupStyles(props);
  const theme = useTheme();
  const [visibleWidgetIds, setVisibleWidgetIds] = useState<string[]>([]);

  useEffect(() => {
    setVisibleWidgetIds(group.widgets.map((widget) => widget.id));
  }, [group]);

  let widgetSelector = undefined;
  if (group.displayMode === 'TOGGLEABLE_AREAS') {
    widgetSelector = (
      <ToggleButtonGroup value={visibleWidgetIds} onChange={(_, newVisibleIds) => setVisibleWidgetIds(newVisibleIds)}>
        {group.widgets.map((widget) => {
          return (
            <ToggleButton className={classes.button} value={widget.id} key={widget.id}>
              <IconOverlay
                iconURL={widget.iconURL}
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

  let toolbar = null;
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
          .map((widget) => (
            <PropertySection
              editingContextId={editingContextId}
              formId={formId}
              widget={widget}
              readOnly={readOnly}
              key={widget.id}
            />
          ))}
      </div>
    </div>
  );
};
