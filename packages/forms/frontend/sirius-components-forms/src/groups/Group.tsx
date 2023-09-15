/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
import { ServerContext, ServerContextValue, getCSSColor } from '@eclipse-sirius/sirius-components-core';
import { makeStyles, Theme } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import ToggleButton from '@material-ui/lab/ToggleButton';
import ToggleButtonGroup from '@material-ui/lab/ToggleButtonGroup';
import { useContext, useEffect, useState } from 'react';
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
      marginBottom: theme.spacing(2),
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
  buttonIcon: {
    marginRight: theme.spacing(1),
    position: 'absolute',
    top: 0,
    left: 0,
  },
  iconContainer: {
    position: 'relative',
    width: '16px',
    height: '16px',
  },
}));

export const Group = ({ editingContextId, formId, group, widgetSubscriptions, setSelection, readOnly }: GroupProps) => {
  const props: GroupStyleProps = {
    borderStyle: group.borderStyle,
  };

  const classes = useGroupStyles(props);
  const [visibleWidgetIds, setVisibleWidgetIds] = useState<string[]>([]);
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);

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
              {widget.iconURL.length > 0 && (
                <div className={classes.iconContainer}>
                  {widget.iconURL.map((icon, index) => (
                    <img
                      height="16"
                      width="16"
                      key={index}
                      alt={widget.label}
                      src={httpOrigin + icon}
                      className={classes.buttonIcon}
                      style={{ zIndex: index }}
                    />
                  ))}
                </div>
              )}
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
              widgetSubscriptions={widgetSubscriptions}
              setSelection={setSelection}
              readOnly={readOnly}
              key={widget.id}
            />
          ))}
      </div>
    </div>
  );
};
